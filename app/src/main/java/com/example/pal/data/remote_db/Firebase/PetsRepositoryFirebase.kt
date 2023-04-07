package com.example.pal.data.remote_db.Firebase


import com.example.pal.data.models.Dog
import com.example.pal.data.models.Pet
import com.google.firebase.firestore.FirebaseFirestore
import com.example.pal.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall
import java.util.*
import javax.inject.Inject


class PetsRepositoryFirebase @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    PetsRepository {

    // the system language
    private val language = Locale.getDefault().language.toString()

    // the pets collection reference, the pets for adoption
    private var petsRef = FirebaseFirestore.getInstance().collection("pets")

    // the dog collection reference, the dogs info by breed
    private val dogsRef = FirebaseFirestore.getInstance().collection("dogs")

    // the dog collection reference, the dogs info by breed
    private val usersRef = FirebaseFirestore.getInstance().collection("user")


    // get pets filtered by animal type
    override suspend fun getPets(animal: String): Resource<List<Pet>> {

        if (language == "iw")
            petsRef = FirebaseFirestore.getInstance().collection("petsHW")

        // the calls wrapped with safe call to catch exceptions
        return safeCall {
            // filter and get only the dogs / cats
            val query = petsRef.whereEqualTo("animal", animal)
            val snapshot = query.get().await()

            val petsList = mutableListOf<Pet>()

            // iterate on the snapshot i got and convert it to Pet object and push it to the petsList
            for (doc in snapshot) {
                val pet = doc.toObject(Pet::class.java)
                petsList.add(pet)
            }
            Resource.success(petsList)
        }

    }

    override suspend fun getAllPetsById(ids: List<String>): List<Pet> {

        if (language == "iw")
            petsRef = FirebaseFirestore.getInstance().collection("petsHW")
        val petsList = mutableListOf<Pet>()
        return withContext(Dispatchers.IO) {
            if (ids.isNotEmpty()) {
                for (id in ids) {
                    val query = petsRef.whereEqualTo("id", id.toInt())
                    println(id)
                    val snapshot = query.get().await()

                    val pet = snapshot.documents[0].toObject(Pet::class.java)
                    petsList.add(pet!!)

                }
            }
            petsList
        }

    }

    // add the pet to the current user favorites, store the id of the new pet in favorites field
    override suspend fun addToFavorites(id: String) : List<String> {

        return withContext(Dispatchers.IO) {
            var successFlag = false
            val userId = firebaseAuth.currentUser!!.uid
            usersRef.document(userId).update("favorites", FieldValue.arrayUnion(id))
                .addOnSuccessListener {
                    successFlag = true
                }.addOnFailureListener {
                    successFlag = false
                }
            usersRef.document(userId).get().await().get("favorites") as List<String>
        }
    }

    // remove the pet from the current user favorites, remove the id of the pet from favorites field
    override suspend fun removePetFromFavorites(id: String): List<String> {
        return withContext(Dispatchers.IO) {
            var successFlag = false
            val userId = firebaseAuth.currentUser!!.uid
            usersRef.document(userId).update("favorites", FieldValue.arrayRemove(id))
                .addOnSuccessListener {
                    successFlag = true
                }.addOnFailureListener {
                    successFlag = false
                }
            usersRef.document(userId).get().await().get("favorites") as List<String>
        }
    }

    override suspend fun getPet(id: Int): Resource<Pet> {

        if (language == "iw")
            petsRef = FirebaseFirestore.getInstance().collection("petsHW")


        return safeCall {
            // Retrieve the document with the given id
            val document = petsRef.document(id.toString()).get().await()

            // Convert the document to a Pet object
            val pet = document.toObject(Pet::class.java)

            Resource.success(pet!!)
        }
    }

    override suspend fun findDogByBreed(breed: String): Resource<Dog> {

        return safeCall {
            // Retrieve the document with the given breed
            val query = dogsRef.whereEqualTo("Breed", breed)
            val document = query.get().await()

            // Convert the document to Dog type object
            val dog = document.documents[0].toObject(Dog::class.java)

            Resource.success(dog!!)
        }
    }

    // get the dog info by breed
    override suspend fun getDogs(): Resource<List<Dog>> {

        return safeCall {
            val snapshot = dogsRef.get().await()
            val dogsList = mutableListOf<Dog>()

            // iterate on the snapshot i got and convert it to Pet object and push it to the petsList
            for (doc in snapshot) {
                val dog = doc.toObject(Dog::class.java)
                dogsList.add(dog)
            }
            Resource.success(dogsList)

        }
    }

}