package newstart.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import newstart.Activity_Main;
import newstart.R;
import newstart.activities.Activity_Workout_CreateEditExercise;
import newstart.activities.Activity_Workout_EditPlans;
import newstart.activities.Activity_Workout_EditRoutines;
import newstart.recyclerview.Adapter_Workout_Exercise;
import newstart.recyclerview.Adapter_Workout_Routine;
import newstart.recyclerview.Item_Workout_Exercise;
import newstart.recyclerview.Item_Workout_Routine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class Fragment_Workout extends Fragment implements Adapter_Workout_Routine.Interface_Workout_Routine, Adapter_Workout_Exercise.Interface_Workout_Exercises, AdapterView.OnItemSelectedListener {

    private String[] workoutPlans = new String[0];
    private final ArrayList<Item_Workout_Routine> workoutRoutines = new ArrayList<>();
    private final ArrayList<Item_Workout_Exercise> workoutExercises = new ArrayList<>();
    private int selectedPlanIdx = -1;
    private int selectedRoutineIdx = -1;
    private RecyclerView recyclerViewRoutines;
    private RecyclerView recyclerViewExercises;
    private Adapter_Workout_Routine adapterRoutines;
    private Adapter_Workout_Exercise adapterExercises;

    private boolean fabOpen = false;
    private FloatingActionButton fabMain;
    private LinearLayout linearLayoutFABSub01;
    private LinearLayout linearLayoutFABSub02;
    private LinearLayout linearLayoutFABSub03;
    private View backgroundBlur;

    private TextView sectionTitleRoutines;
    private TextView sectionTitleExercises;


    private String[] loadPlansFromDatabase() {
        if (getActivity() == null) return new String[0];
        String[] loadedPlans = new String[0];
        try (Cursor cursor = ((Activity_Main) getActivity()).databaseHelper.getWorkoutPlans()) {
            if (cursor != null && cursor.getCount() > 0) {
                loadedPlans = new String[cursor.getCount()];
                int j = 0;
                while (cursor.moveToNext()) {
                    loadedPlans[j] = cursor.getString(0);
                    j++;
                }
            }
        } catch (Exception e) {
            Log.e("Fragment_Workout", "Error loading plans", e);
        }
        return loadedPlans;
    }

    private ArrayList<Item_Workout_Routine> loadRoutinesFromDatabase(String workoutPlan) {
        ArrayList<Item_Workout_Routine> loadedRoutines = new ArrayList<>();
        if (getActivity() == null) return loadedRoutines;
        try (Cursor cursor = ((Activity_Main) getActivity()).databaseHelper.getWorkoutRoutines(workoutPlan)) {
            if (cursor != null && cursor.getCount() > 0) {
                boolean first = true;
                while (cursor.moveToNext()) {
                    loadedRoutines.add(new Item_Workout_Routine(cursor.getString(0), first));
                    first = false;
                }
            }
        } catch (Exception e) {
            Log.e("Fragment_Workout", "Error loading routines", e);
        }
        return loadedRoutines;
    }

    private ArrayList<Item_Workout_Exercise> loadExercisesFromDatabase(String workoutPlan, String workoutRoutine) {
        ArrayList<Item_Workout_Exercise> loadedExercises = new ArrayList<>();
        if (getActivity() == null) return loadedExercises;
        try (Cursor cursor = ((Activity_Main) getActivity()).databaseHelper.getWorkoutExercises(workoutPlan, workoutRoutine)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    loadedExercises.add(
                            new Item_Workout_Exercise(
                                    cursor.getString(2),  // Title (index 2 in table: plan, routine, name, sets, reps, weight)
                                    cursor.getInt(3),  // Sets
                                    cursor.getInt(4),  // Reps
                                    cursor.getDouble(5)  // Weight
                            )
                    );
                }
            }
        } catch (Exception e) {
            Log.e("Fragment_Workout", "Error loading exercises", e);
        }
        return loadedExercises;
    }

    private void toggleFABMenu() {
        if (fabOpen) {
            fabOpen = false;
            fabMain.setImageResource(R.drawable.ic_baseline_add_circle_24);
            linearLayoutFABSub01.animate().translationY(0);
            linearLayoutFABSub02.animate().translationY(0);
            linearLayoutFABSub03.animate().translationY(0);
            linearLayoutFABSub01.setVisibility(View.INVISIBLE);
            linearLayoutFABSub02.setVisibility(View.INVISIBLE);
            linearLayoutFABSub03.setVisibility(View.INVISIBLE);
            backgroundBlur.setVisibility(View.GONE);
        } else {
            fabOpen = true;
            backgroundBlur.setVisibility(View.VISIBLE);
            fabMain.setImageResource(R.drawable.ic_baseline_remove_circle_24);
            linearLayoutFABSub01.setVisibility(View.VISIBLE);
            linearLayoutFABSub02.setVisibility(View.VISIBLE);
            linearLayoutFABSub03.setVisibility(View.VISIBLE);
            linearLayoutFABSub01.animate().translationY(-170);
            linearLayoutFABSub02.animate().translationY(-340);
            linearLayoutFABSub03.animate().translationY(-510);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        workoutPlans = loadPlansFromDatabase();
        if (workoutPlans.length > 0) {
            selectedPlanIdx = 0;
            workoutRoutines.addAll(loadRoutinesFromDatabase(workoutPlans[0]));
            if (!workoutRoutines.isEmpty()) {
                selectedRoutineIdx = 0;
                workoutExercises.addAll(loadExercisesFromDatabase(workoutPlans[0], workoutRoutines.get(0).getTitle()));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sectionTitleRoutines = view.findViewById(R.id.sectionTitleRoutines);
        sectionTitleExercises = view.findViewById(R.id.sectionTitleExercises);

        Spinner spinnerPlans = view.findViewById(R.id.spinnerWorkoutPlans);
        spinnerPlans.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapterPlans = new ArrayAdapter<>(requireContext(), R.layout.spinner_item_purple_middle, workoutPlans);
        adapterPlans.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlans.setAdapter(adapterPlans);

        recyclerViewRoutines = view.findViewById(R.id.recyclerViewRoutines);
        adapterRoutines = new Adapter_Workout_Routine(workoutRoutines, this);
        recyclerViewRoutines.setAdapter(adapterRoutines);
        recyclerViewRoutines.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        if (workoutRoutines.isEmpty()) {
            sectionTitleRoutines.setVisibility(View.INVISIBLE);
        }

        recyclerViewExercises = view.findViewById(R.id.recyclerViewExercises);
        adapterExercises = new Adapter_Workout_Exercise(workoutExercises, this);
        recyclerViewExercises.setAdapter(adapterExercises);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (workoutExercises.isEmpty()) {
            sectionTitleExercises.setVisibility(View.INVISIBLE);
        }

        // FAB-Views
        linearLayoutFABSub01 = view.findViewById(R.id.linearLayoutFAB01);
        linearLayoutFABSub02 = view.findViewById(R.id.linearLayoutFAB02);
        linearLayoutFABSub03 = view.findViewById(R.id.linearLayoutFAB03);
        backgroundBlur = view.findViewById(R.id.fragmentExercisesBlur);
        fabMain = view.findViewById(R.id.fabExercisesMain);
        FloatingActionButton fabSub01 = view.findViewById(R.id.fabExercises01);
        FloatingActionButton fabSub02 = view.findViewById(R.id.fabExercises02);
        FloatingActionButton fabSub03 = view.findViewById(R.id.fabExercises03);

        fabMain.setOnClickListener(v -> toggleFABMenu());
        backgroundBlur.setOnClickListener(v -> toggleFABMenu());

        fabSub01.setOnClickListener(v -> {
            if (workoutRoutines.isEmpty()) {
                Snackbar.make(v, "Please create at least one workout routine first!", Snackbar.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(v.getContext(), Activity_Workout_CreateEditExercise.class);
            intent.putExtra("date", ((Activity_Main) requireActivity()).date);
            startActivity(intent);
        });

        fabSub02.setOnClickListener(v -> {
            if (workoutPlans.length <= 0) {
                Toast.makeText(getContext(), "You must create at least 1 workout plan first!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(v.getContext(), Activity_Workout_EditRoutines.class);
            startActivity(intent);
        });

        fabSub03.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Activity_Workout_EditPlans.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == selectedPlanIdx) return;

        selectedPlanIdx = position;

        workoutRoutines.clear();
        workoutRoutines.addAll(loadRoutinesFromDatabase(workoutPlans[selectedPlanIdx]));
        adapterRoutines.notifyDataSetChanged();

        if (workoutRoutines.isEmpty()) {
            selectedRoutineIdx = -1;
            workoutExercises.clear();
            adapterExercises.notifyDataSetChanged();
            sectionTitleRoutines.setVisibility(View.INVISIBLE);
            sectionTitleExercises.setVisibility(View.INVISIBLE);
            return;
        }

        sectionTitleRoutines.setVisibility(View.VISIBLE);
        selectedRoutineIdx = 0;
        
        workoutExercises.clear();
        workoutExercises.addAll(loadExercisesFromDatabase(workoutPlans[selectedPlanIdx], workoutRoutines.get(0).getTitle()));
        adapterExercises.notifyDataSetChanged();

        if (workoutExercises.isEmpty()) {
            sectionTitleExercises.setVisibility(View.INVISIBLE);
        } else {
            sectionTitleExercises.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onRoutineItemClick(int itemPosition) {
        if (selectedRoutineIdx == -1 || itemPosition == selectedRoutineIdx) return;

        workoutRoutines.get(itemPosition).setIsSelected(true);
        workoutRoutines.get(selectedRoutineIdx).setIsSelected(false);
        adapterRoutines.notifyItemChanged(itemPosition);
        adapterRoutines.notifyItemChanged(selectedRoutineIdx);

        selectedRoutineIdx = itemPosition;

        workoutExercises.clear();
        workoutExercises.addAll(loadExercisesFromDatabase(workoutPlans[selectedPlanIdx], workoutRoutines.get(selectedRoutineIdx).getTitle()));
        adapterExercises.notifyDataSetChanged();

        if (workoutExercises.isEmpty()) {
            sectionTitleExercises.setVisibility(View.INVISIBLE);
        } else {
            sectionTitleExercises.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onExerciseItemClick(int itemPosition) {
        Intent intent = new Intent(getContext(), Activity_Workout_CreateEditExercise.class);
        intent.putExtra("date", ((Activity_Main) requireActivity()).date);
        intent.putExtra("mode", "edit");
        intent.putExtra("planName", workoutPlans[selectedPlanIdx]);
        intent.putExtra("routineName", workoutRoutines.get(selectedRoutineIdx).getTitle());
        intent.putExtra("exerciseName", workoutExercises.get(itemPosition).getTitle());
        intent.putExtra("exerciseSets", workoutExercises.get(itemPosition).getSets());
        intent.putExtra("exerciseReps", workoutExercises.get(itemPosition).getRepetitions());
        intent.putExtra("exerciseWeight", workoutExercises.get(itemPosition).getWeight());
        startActivity(intent);
    }
}
