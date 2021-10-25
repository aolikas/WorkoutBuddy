package my.aolika.workoutbuddy.adapter

import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

abstract class FirestoreAdapter<VH: RecyclerView.ViewHolder>(
    private val query: Query) : RecyclerView.Adapter<VH>(),
                                EventListener<QuerySnapshot> {

     private var registration: ListenerRegistration? = null
    private val snapshot = ArrayList<DocumentSnapshot>()

    open fun startListening() {
        if(registration == null) {
            registration = query.addSnapshotListener(this)
        }
    }

    open fun stopListening() {
        if(registration != null) {
            registration!!.remove()
            registration = null
        }

        snapshot.clear()
        notifyDataSetChanged()
    }

    override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
        if(error != null) {
            Log.e("onEvent:error", error.toString())
            return
        }

        for(change in value!!.documentChanges) {
            when(change.type) {
                DocumentChange.Type.ADDED -> onDocumentAdded(change)
                DocumentChange.Type.MODIFIED -> onDocumentModify(change)
                DocumentChange.Type.REMOVED -> onDocumentRemoved(change)

            }
        }
    }

    protected open fun onDocumentAdded(change: DocumentChange) {
        snapshot.add(change.newIndex, change.document)
        notifyItemInserted(change.newIndex)
    }

    protected open fun onDocumentModify(change: DocumentChange) {
        if(change.oldIndex == change.newIndex) {
            snapshot[change.oldIndex] == change.document
            notifyItemChanged(change.oldIndex)
        } else {
            snapshot.removeAt(change.oldIndex)
            snapshot.add(change.newIndex, change.document)
            notifyItemMoved(change.oldIndex, change.newIndex)
        }
    }

    protected open fun onDocumentRemoved(change: DocumentChange) {
        snapshot.removeAt(change.oldIndex)
        notifyItemRemoved(change.oldIndex)
    }

    override fun getItemCount(): Int {
        return snapshot.size
    }

    protected open fun getSnapshot(index: Int): DocumentSnapshot? {
        return snapshot[index]
    }
}