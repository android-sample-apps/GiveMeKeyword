package com.mut_jaeryo.data.source.app

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.mut_jaeryo.domain.repositories.AppRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(

): AppRepository {
    override suspend fun getAppVersion() = suspendCancellableCoroutine<String> { coroutine ->
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("app").document("version")

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                coroutine.resume(document?.getString("code") ?: "unknown") {
                }
            } else {
                coroutine.cancel(task.exception)
            }
        }
    }
}