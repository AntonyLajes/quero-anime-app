package com.example.quero_anime_app.ui.model.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseClient {

    companion object{

        private lateinit var INSTANCE: FirebaseAuth
        private lateinit var FIRESTORE_INSTANCE: FirebaseFirestore
        fun getFirebaseClient(): FirebaseAuth{
            if(!::INSTANCE.isInitialized){
                INSTANCE = Firebase.auth
            }
            return INSTANCE
        }

        fun getFirestoreClient(): FirebaseFirestore{
            if(!::FIRESTORE_INSTANCE.isInitialized){
                FIRESTORE_INSTANCE = FirebaseFirestore.getInstance()
            }
            return FIRESTORE_INSTANCE
        }
    }

}