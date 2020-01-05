package com.mut_jaeryo.givmkeyword.utills.Database

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.mut_jaeryo.givmkeyword.view.DrawingSNSItems.DrawingAdapter
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


//이미지 저장, 삭제 , 조회 구현
class FirebaseDB{


    companion object {
        public fun saveDrawing(activity:Activity,keyword: String, image: Bitmap, name: String, content: String): Boolean {

            val db = FirebaseFirestore.getInstance().collection(keyword)
            val doc = db.document() //고유 id를 자동으로 생성


            val imagesRef: StorageReference? = FirebaseStorage.getInstance().reference.child("images/" + doc.id + ".png")


            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data_byte = baos.toByteArray()

            val uploadTask = imagesRef!!.putBytes(data_byte)
            uploadTask.addOnFailureListener {
                Toast.makeText(activity, "서버에 저장이 실패했습니다 ㅠㅠ", Toast.LENGTH_LONG).show()
                Log.d("ImageUpload", "failed")
            }.addOnSuccessListener {
                val data = hashMapOf(
                        "name" to name,
                        "content" to content,
                        "heart" to 0,
                        "hate" to 0
                )
               val now = GregorianCalendar()

                doc.set(data)
                        .addOnSuccessListener {

                            DrawingDB.db.DrawingInsert(doc.id,content,"${now[Calendar.YEAR]}-${now[Calendar.MONTH]+1}-${now[Calendar.DAY_OF_MONTH]}")


                            Toast.makeText(activity, "저장에 성공했습니다.", Toast.LENGTH_LONG).show() }
                        .addOnCanceledListener {
                            Toast.makeText(activity, "서버에 저장이 실패했습니다 ㅠㅠ", Toast.LENGTH_LONG).show()
                            imagesRef.delete()
                            Log.d("Document", "failed")
                        }
            }

            return true
        }


//        public fun getMineDrawings(context: Context): ArrayList<drawingItem> {
//
//            val drawingDB = DrawingDB(context)
//            drawingDB.open()
//            val array: ArrayList<drawingItem> = drawingDB.getMyDrawing()
//            drawingDB.close()
//            return array
//        }
//
        fun changeHeart(item: drawingItem,context: Context){
            val db = FirebaseFirestore.getInstance()


            val sfDocRef = db.collection(item.keyword).document(item.id)

            db.runTransaction { transaction ->
                val snapshot = transaction.get(sfDocRef)

                // Note: this could be done without a transaction
                //       by updating the population using FieldValue.increment()
                var heartNum = snapshot.getLong("heart")!!.toInt()
                if(item.isHeart){

                    heartNum++
                }
                else {
                    if(heartNum>0)
                    heartNum--
                }
                transaction.update(sfDocRef, "heart", heartNum)

                // Success
                null
            }.addOnSuccessListener {
                DrawingDB.db.changeHeart(item.id,item.isHeart)
                item.heart = if (item.isHeart) item.heart +1 else item.heart - 1
                item.isHeart = !item.isHeart
            }.addOnFailureListener {
                        Toast.makeText(context,"현재 서버문제로 좋아요 기능을 사용할 수 없습니다.",Toast.LENGTH_SHORT).show()
                    }

        }
        fun addHate(item: drawingItem,context: Context){
            val db = FirebaseFirestore.getInstance()


            val sfDocRef = db.collection(item.keyword).document(item.id)

            db.runTransaction { transaction ->
                val snapshot = transaction.get(sfDocRef)

                // Note: this could be done without a transaction
                //       by updating the population using FieldValue.increment()
                val heartNum = snapshot.getLong("hate")!!.toInt() + 1

                transaction.update(sfDocRef, "heart", heartNum)

                // Success
                null
            }.addOnSuccessListener {  }
                    .addOnFailureListener {
                        Toast.makeText(context,"현재 서버문제로 신고 기능을 사용할 수 없습니다.",Toast.LENGTH_SHORT).show()
                    }

        }
    }
}