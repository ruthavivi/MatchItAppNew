package com.example.class3demo2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.class3demo2.model.Teacher;
import com.example.class3demo2.teacheradapter.MyAdapter;


public class TeachersListFragment extends Fragment {
    TeachersListFragmentViewModel viewModel;
    View view;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(TeachersListFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teachers_list, container, false);

        RecyclerView list = view.findViewById(R.id.studentlist_list_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);



        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Teacher st = viewModel.getData().getValue().get(position);
                Log.d("TAG","row was clicked " + position);
                TeachersListFragmentDirections.ActionStudentsListFragmentToStudentDetailsFragment2 action = TeachersListFragmentDirections.actionStudentsListFragmentToStudentDetailsFragment2(st.getId());
                Navigation.findNavController(v).navigate(action);
            }
        });

        swipeRefresh = view.findViewById(R.id.studentlist_swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        setHasOptionsMenu(true);
        if (viewModel.getData() == null ) {refreshData();};

        viewModel.getData().observe(getViewLifecycleOwner(), (teachers)->{
            adapter.updateListTeachers(teachers);
        });

        return view;
    }

    private void refreshData() {
//        swipeRefresh.setRefreshing(true);


//        Model.instance.getAllTeachers(new Model.GetAllTeachersListener() {
//            @Override
//            public void onComplete(List<Teacher> d) {
//                viewModel.setData(d);
//                adapter.notifyDataSetChanged();
//                if (swipeRefresh.isRefreshing()) {
//                    swipeRefresh.setRefreshing(false);
//                }
//            }
//        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.teacher_list_menu,menu);
    }

}