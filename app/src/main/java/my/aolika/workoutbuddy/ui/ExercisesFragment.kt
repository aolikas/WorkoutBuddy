package my.aolika.workoutbuddy.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import my.aolika.workoutbuddy.R
import my.aolika.workoutbuddy.adapter.ExerciseRecyclerAdapter
import my.aolika.workoutbuddy.databinding.FragmentExercisesBinding
import my.aolika.workoutbuddy.model.User


class ExercisesFragment : Fragment() {

    //ViewBinding
    private lateinit var binding: FragmentExercisesBinding

    private lateinit var recyclerView: RecyclerView
     private lateinit var exerciseArrayList: ArrayList<User>
     private lateinit var mAdapter: ExerciseRecyclerAdapter
     private lateinit var db: FirebaseFirestore
    //FirebaseAuth
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_exercises,
            container,
            false)
        setHasOptionsMenu(true)

        binding.rvExercises.layoutManager = LinearLayoutManager(context)
        binding.rvExercises.setHasFixedSize(true)

        exerciseArrayList = arrayListOf()

        mAdapter = ExerciseRecyclerAdapter(exerciseArrayList)

        binding.rvExercises.adapter = mAdapter





        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_exercise_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_exercise -> {
                val action =
                    ExercisesFragmentDirections.actionExercisesFragmentToAddExerciseFragment()
                findNavController().navigate(action)
                true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}