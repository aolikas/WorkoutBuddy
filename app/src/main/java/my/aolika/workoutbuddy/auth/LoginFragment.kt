package my.aolika.workoutbuddy.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import my.aolika.workoutbuddy.R
import my.aolika.workoutbuddy.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    //ViewBinding
    private lateinit var binding: FragmentLoginBinding
    //FirebaseAuth
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false)

        //init FirebaseAuth
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            loginUser()
        }

        binding.btnCreateAccount.setOnClickListener {
            view.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistrationFragment())
        }

        binding.btnForgotPassword.setOnClickListener {
            view.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }
    }

    private fun loginUser() {
        val email = binding.etLoginEmail.text.toString().trim()
        val password = binding.etLoginPassword.text.toString().trim()

        when {
            TextUtils.isEmpty(email) -> {
                binding.etLoginEmail.error = "Please enter an email"
                return
            }
            TextUtils.isEmpty(password) -> {
                binding.etLoginPassword.error = "Please enter a password"
                return
            }
            else -> {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {

                            Toast.makeText(context,
                                "Success",
                                Toast.LENGTH_SHORT).show()
                            checkLoggedState()

                        } else {
                            Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }

            }

        }
    }

    private fun checkLoggedState() {
        if(auth.currentUser != null) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoggedState()
    }
}