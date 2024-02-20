package com.coursework.drivingschool.coursantMenu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.coursework.drivingschool.MainActivity;
import com.coursework.drivingschool.R;
import com.coursework.drivingschool.roles.Coursant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileCoursantFragment extends Fragment {

    public ProfileCoursantFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth auth;
    private final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://driving-school-cadet-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference data;

    private ImageView profile_im;
    private Dialog dialog;
    private TextView name;
    private TextView surname;
    private TextView patronomyc;
    private TextView birthday;
    private TextView phone;
    private TextView email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile_coursant, container, false);

        MaterialButton logout_btn = profileView.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        profile_im = profileView.findViewById(R.id.profile_im);
        name = profileView.findViewById(R.id.name);
        surname = profileView.findViewById(R.id.surname);
        patronomyc = profileView.findViewById(R.id.patronomyc);
        birthday = profileView.findViewById(R.id.birthday);
        phone = profileView.findViewById(R.id.phone);
        email = profileView.findViewById(R.id.email);
        data = mDatabase.getReference("Coursants/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        dialog = new Dialog(getActivity());
        ImageView phoneEdit = profileView.findViewById(R.id.phoneEdit);
        ImageView emailEdit = profileView.findViewById(R.id.emailEdit);

        phoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneChangeDialog();
            }
        });

        emailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmailChangeDialog();
            }
        });

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Coursant user = snapshot.getValue(Coursant.class);

                if(user.getUrl_im() != null) {
                    Picasso.get().load(user.getUrl_im()).into(profile_im);
                }
                name.setText(user.getName());
                surname.setText(user.getSurname());
                patronomyc.setText(user.getPatronomyc());
                birthday.setText(user.getBirthday());
                phone.setText(user.getPhone());
                email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return profileView;
    }

    private void showEmailChangeDialog() {
        dialog.setContentView(R.layout.dialog_edit_email);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        MaterialButton accept_btn = dialog.findViewById(R.id.accept_btn);
        EditText newEmail = dialog.findViewById(R.id.newEmail);
        EditText password = dialog.findViewById(R.id.password);
        TextView oldEmail = dialog.findViewById(R.id.textView9);
        oldEmail.setText(email.getText().toString());

        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newEmail.getText().toString().equals(email.getText().toString())) {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(), password.getText().toString()); // Current Login Credentials

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.updateEmail(newEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        data.child("email").setValue(newEmail.getText().toString());
                                        Toast.makeText(getActivity(), "E-mail успешно изменен", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    } else {
                                        Toast.makeText(getActivity(), "Произошла ошибка", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }

            }
        });
        dialog.show();
    }

    private void showPhoneChangeDialog() {
        dialog.setContentView(R.layout.dialog_edit_phone);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        MaterialButton accept_btn = dialog.findViewById(R.id.accept_btn);
        EditText newPhone = dialog.findViewById(R.id.editTextPhone);
        newPhone.setText(phone.getText().toString());

        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!newPhone.getText().toString().equals(phone.getText().toString())) {
                    data.child("phone").setValue(newPhone.getText().toString());
                    Toast.makeText(getActivity(), "Номер успешно изменен", Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        });
        dialog.show();
    }
}