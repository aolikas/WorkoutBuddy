package my.aolika.workoutbuddy.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import my.aolika.workoutbuddy.R
import my.aolika.workoutbuddy.databinding.FragmentAddExerciseBinding


class AddExerciseFragment : Fragment() {

    //ViewBinding
    private lateinit var binding: FragmentAddExerciseBinding

    //FirebaseAuth
    private lateinit var auth: FirebaseAuth

    //Firestore
    private lateinit var dbFirestore: FirebaseFirestore

    //NavController
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_exercise,
            container,
            false
        )

        //init FirebaseAuth
        auth = FirebaseAuth.getInstance()

        //init Firestore
        dbFirestore = FirebaseFirestore.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init NavController
        navController = findNavController()

        binding.fabAddExercise.setOnClickListener {
            checkExercise()
        }

    }

    private fun checkExercise() {
        val exercise = binding.etAddExercise.text.toString().trim()

        when {
            TextUtils.isEmpty(exercise) -> {
                binding.etAddExercise.error = "Enter an exercise name "
                return
            }
            else -> {
                insertExercise(exercise)

            }
        }
    }


    private fun insertExercise(exercise: String) {
        val currentUser = auth.currentUser
        var userId = ""
        currentUser?.let {
            userId = currentUser.uid
        }

        val ref = dbFirestore.collection("users").document(userId)

        ref.update("exercises", FieldValue.arrayUnion(exercise))
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Exercise successfully added",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show() }
    }
}