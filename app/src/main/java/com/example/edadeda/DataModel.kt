package com.example.edadeda

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

open class DataModel: ViewModel() {
    val curRecept: MutableLiveData<Recept> by lazy {
        MutableLiveData<Recept>()
    }
    val curFragment: MutableLiveData<Fragment> by lazy {
        MutableLiveData<Fragment>()
    }
    val client: MutableLiveData<GoogleSignInClient> by lazy {
        MutableLiveData<GoogleSignInClient>()
    }
}