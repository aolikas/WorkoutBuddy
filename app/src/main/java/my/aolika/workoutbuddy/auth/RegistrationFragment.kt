package my.aolika.workoutbuddy.auth


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
import com.google.firebase.firestore.FirebaseFirestore
import my.aolika.workoutbuddy.R
import my.aolika.workoutbuddy.databinding.FragmentRegistrationBinding
import my.aolika.workoutbuddy.model.User


class RegistrationFragment : Fragment() {

    //ViewBinding
    private lateinit var binding: FragmentRegistrationBinding

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
            R.layout.fragment_registration,
            container, false
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

        binding.btnRegister.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser() {
        val name = binding.etRegisterName.text.toString().trim()
        val email = binding.etRegisterEmail.text.toString().trim()
        val password = binding.etRegisterPassword.text.toString().trim()

        when {
            TextUtils.isEmpty(name) -> {
                binding.etRegisterName.error = "Please enter a name"
                return
            }
            TextUtils.isEmpty(email) -> {
                binding.etRegisterEmail.error = "Please enter an email"
                return
            }
            TextUtils.isEmpty(password) -> {
                binding.etRegisterPassword.error = "Please enter a password"
                return
            }
            else -> {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Success",
                                Toast.LENGTH_SHORT
                            ).show()
                            createUser(name, email)
                            navController.navigate(RegistrationFragmentDirections.actionRegistrationFragmentToHomeFragment())

                        } else {
                            Toast.makeText(
                                context, "Registration failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

            }

        }

    }

    private fun createUser(name: String, email: String) {
     //   val user = User()
       // user.name = name
       // user.email = email
        val user = User(name, email)
        insertUser(user)
    }

    private fun insertUser(user: User) {
        val currentUser = auth.currentUser
        var userId = ""
        currentUser?.let {
            userId = currentUser.uid
        }

        val ref = dbFirestore.collection("users")
        ref.document(userId).set(user)
            .addOnCompleteListener{
             when{
                 it.isSuccessful -> {
//                     Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
                 }
                 else -> {
           //          Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show()
                 }
             }
            }
    }

}
