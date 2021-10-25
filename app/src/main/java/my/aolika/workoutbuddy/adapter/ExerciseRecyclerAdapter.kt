package my.aolika.workoutbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.aolika.workoutbuddy.R
import my.aolika.workoutbuddy.databinding.ItemExerciseBinding
import my.aolika.workoutbuddy.model.User

class ExerciseRecyclerAdapter(private val exerciseList: ArrayList<User>)
    : RecyclerView.Adapter<ExerciseRecyclerAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val exercise: User  = exerciseList[position]
        holder.tvExercise.text = exercise.exercises.toString()
    }

    override fun getItemCount(): Int {
     return exerciseList.size
    }

    public class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvExercise: TextView = itemView.findViewById(R.id.tv_item_exercise)


    }
}