package my.aolika.workoutbuddy

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import my.aolika.workoutbuddy.databinding.FragmentHomeBinding
import my.aolika.workoutbuddy.databinding.FragmentLoginBinding


class HomeFragment : Fragment() {

    //ViewBinding
    private lateinit var binding: FragmentHomeBinding
    //FirebaseAuth
    private lateinit var auth: FirebaseAuth
    //Firestore
    private lateinit var dbFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false)

        //init FirebaseAuth
        auth = FirebaseAuth.getInstance()

        //init Firestore
        dbFirestore = FirebaseFirestore.getInstance()
        readFirestore()

        return binding.root
    }

    private fun readFirestore() {
        val currentUser = auth.currentUser
        var userId = ""
        currentUser?.let {
            userId = currentUser.uid
        }
        dbFirestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if(document != null) {
                    binding.tvNameResult.text = document.data.toString()
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
        }
    }


}