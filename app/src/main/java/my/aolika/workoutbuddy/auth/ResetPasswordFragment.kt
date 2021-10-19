package my.aolika.workoutbuddy.auth

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import my.aolika.workoutbuddy.R

import my.aolika.workoutbuddy.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : Fragment() {

    //ViewBinding
    private lateinit var binding: FragmentResetPasswordBinding

    //FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_reset_password,
            container,
            false
        )

        //init FirebaseAuth
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnResetPassword.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val email = binding.etResetPasswordEmail.text.toString().trim()

        when {
            TextUtils.isEmpty(email) -> {
                binding.etResetPasswordEmail.error = "Please enter an email"
                return
            }
            else -> {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(
                                context,
                                "Check your email",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context, "Sending is failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
            }

        }
    }


}