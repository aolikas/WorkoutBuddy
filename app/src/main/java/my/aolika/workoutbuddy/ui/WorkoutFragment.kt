package my.aolika.workoutbuddy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import my.aolika.workoutbuddy.R
import my.aolika.workoutbuddy.databinding.FragmentWorkoutBinding


class WorkoutFragment : Fragment() {

    //ViewBinding
    private lateinit var binding: FragmentWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_workout,
            container,
            false)


        return binding.root
    }
}