package com.mut_jaeryo.givmkeyword.utills.Database

import android.app.Activity
import android.graphics.Bitmap
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mut_jaeryo.givmkeyword.view.Items.drawingItem


//이미지 저장, 삭제 , 조회 구현
class FirebaseDB(val activity: Activity) {


    public fun saveDrawing(keyword:String,image: Bitmap) : Boolean{

        val db = FirebaseFirestore.getInstance()

        val storage = FirebaseStorage.getInstance()

        var storageRef = storage.reference  //storage 참조

        val data = drawingItem(keyword,,0,0)

        db.collection(keyword)
                .add(data)
                .addOnSuccessListener { Toast.makeText(activity,"저장에 성공했습니다.",Toast.LENGTH_LONG).show() }
                .addOnCanceledListener { Toast.makeText(activity,"서버에 저장이 실패했습니다 ㅠㅠ",Toast.LENGTH_LONG).show() }
        return true;
    }

    public fun getDrawings(keyword: String)
    {

    }
}