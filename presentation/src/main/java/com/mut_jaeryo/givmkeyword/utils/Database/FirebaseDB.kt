package com.mut_jaeryo.givmkeyword.utils.Database

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mut_jaeryo.givmkeyword.utils.AlertUtills
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import java.io.ByteArrayOutputStream
import java.util.*


//이미지 저장, 삭제 , 조회 구현
class FirebaseDB {


    companion object {
        public fun saveDrawing(activity: Activity, keyword: String, image: Bitmap, name: String, content: String): Boolean {

            val db = FirebaseFirestore.getInstance()
            var DrawDoc = db.collection(keyword).document() //고유 id를 자동으로 생성


            val pDialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
            pDialog.progressHelper.barColor = Color.parseColor("#4285F4")
            pDialog.titleText = "Uploading"
            pDialog.setCancelable(false)
            pDialog.show()

            val imagesRef: StorageReference? = FirebaseStorage.getInstance().reference.child("images/" + DrawDoc.id + ".png")


            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val data_byte = baos.toByteArray()


            val uploadTask = imagesRef!!.putBytes(data_byte)
            uploadTask.addOnFailureListener {
                pDialog.dismissWithAnimation()
                AlertUtills.ErrorAlert(activity, "현재 서버문제로 저장에 실패했습니다.")
                Log.d("ImageUpload", "failed")

            }.addOnSuccessListener {
                var data = hashMapOf(
                        "name" to name,
                        "content" to content,
                        "heart" to 0,
                        "hate" to 0
                )
                val now = GregorianCalendar()

                DrawDoc.set(data)
                        .addOnSuccessListener {

                            DrawingDB.db.DrawingInsert(DrawDoc.id, content, "${now[Calendar.YEAR]}-${now[Calendar.MONTH] + 1}-${now[Calendar.DAY_OF_MONTH]}")

                            val UserDoc = db.collection("users").document(BasicDB.getName(activity.applicationContext)!!).collection("images").document(DrawDoc.id)
                            data = hashMapOf(
                                    "keyword" to keyword
                            )

                            UserDoc.set(data)
                                    .addOnSuccessListener {
                                        pDialog.dismissWithAnimation()
                                        AlertUtills.SuccessAlert(activity, "저장에 성공했습니다")

                                        BasicDB.setWork(activity, BasicDB.getWork(activity) + 1)

                                    }.addOnFailureListener {
                                        AlertUtills.ErrorAlert(activity, "현재 서버문제로 저장에 실패했습니다.")

                                    }


                        }
                        .addOnCanceledListener {
                            pDialog.dismissWithAnimation()
                            AlertUtills.ErrorAlert(activity, "현재 서버문제로 저장에 실패했습니다.")
                            imagesRef.delete()

                            Log.d("Document", "failed")
                        }
                        .addOnFailureListener {
                            pDialog.dismissWithAnimation()
                            AlertUtills.ErrorAlert(activity.applicationContext, "현재 서버문제로 저장에 실패했습니다.")
                            imagesRef.delete()

                        }
            }

            return true
        }


        fun changeHeart(item: drawingItem, context: Context) {
            val db = FirebaseFirestore.getInstance()


            var sfDocRef = db.collection(item.keyword ?: "").document(item.id ?: "")

            db.runTransaction { transaction ->
                val snapshot = transaction.get(sfDocRef)

                // Note: this could be done without a transaction
                //       by updating the population using FieldValue.increment()
                var heartNum = snapshot.getLong("heart")!!.toInt()
                if (!item.isHeart) {
                    heartNum++
                } else {
                    if (heartNum > 0)
                        heartNum--
                }
                transaction.update(sfDocRef, "heart", heartNum)

                // Success
                null
            }.addOnSuccessListener {
                DrawingDB.db.changeHeart(item.id ?: "", item.isHeart)
                item.heart = if (!item.isHeart) item.heart + 1 else item.heart - 1

                item.isHeart = !item.isHeart


                val doc = sfDocRef.collection("hearts").document(BasicDB.getName(context)!!)

                if (item.isHeart) {

                    val data = hashMapOf(
                            "exist" to true
                    )

                    doc.set(data)
                            .addOnSuccessListener {

                            }.addOnFailureListener {
                                AlertUtills.ErrorAlert(context, "현재 서버문제로 좋아요 기능이 실패했습니다.")
                            }.addOnCanceledListener {
                                AlertUtills.ErrorAlert(context, "현재 서버문제로 좋아요 기능이 실패했습니다.")
                            }
                } else {
                    doc.delete()
                }

            }.addOnFailureListener {
                AlertUtills.ErrorAlert(context, "현재 서버문제로 좋아요 기능을 사용할 수 없습니다.")
            }

        }

        fun addHate(item: drawingItem, context: Context) {
            val db = FirebaseFirestore.getInstance()


            val sfDocRef = db.collection(item.keyword ?: "").document(item.id ?: "")

            db.runTransaction { transaction ->
                val snapshot = transaction.get(sfDocRef)

                // Note: this could be done without a transaction
                //       by updating the population using FieldValue.increment()
                val heartNum = snapshot.getLong("hate")!!.toInt() + 1
                transaction.update(sfDocRef, "heart", heartNum)

                // Success
                null
            }.addOnSuccessListener { }
                    .addOnFailureListener {
//                        Toast.makeText(context,"현재 서버문제로 신고 기능을 사용할 수 없습니다.",Toast.LENGTH_SHORT).show()
                        AlertUtills.ErrorAlert(context, "현재 서버문제로 신고 기능을 사용할 수 없습니다.")
                    }

        }
    }

}