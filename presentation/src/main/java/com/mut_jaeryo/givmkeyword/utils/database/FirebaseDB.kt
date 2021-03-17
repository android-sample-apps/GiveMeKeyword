package com.mut_jaeryo.givmkeyword.utils.database

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.mut_jaeryo.givmkeyword.utils.AlertUtills
import com.mut_jaeryo.givmkeyword.entities.DrawingItem


//이미지 저장, 삭제 , 조회 구현
class FirebaseDB {


    companion object {

        fun changeHeart(item: DrawingItem, context: Context) {
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


                val doc = sfDocRef.collection("hearts").document(Preference.getName(context)!!)

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

        fun addHate(item: DrawingItem, context: Context) {
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