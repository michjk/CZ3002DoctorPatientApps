package ntu.com.mylife.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.utilities.Base64;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import ntu.com.mylife.R;
import ntu.com.mylife.common.entity.applicationentity.SharedPreferencesKey;
import ntu.com.mylife.common.service.DatabaseDaoUser;
import ntu.com.mylife.common.service.DatabaseDaoUserImpl;
import ntu.com.mylife.common.service.SharedPreferencesService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileView#  } factory method to
 * create an instance of this fragment.
 */
public class ProfileView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;
    private EditText textFullName,textAge,textDob,textBloodType,textAllergy,textMedicalRecord;
    private CircleImageView profileImage;
    private Button buttonSave;
    private Bitmap bitmap;
    private SharedPreferencesService sharedPreferencesService;

    public ProfileView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesService = new SharedPreferencesService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  =  inflater.inflate(R.layout.fragment_profile_view, container, false);
        //all attribute is defined here
        textFullName = (EditText) rootView.findViewById(R.id.fullname_profile_editText);
        textAge      = (EditText) rootView.findViewById(R.id.age_profile_editText);
        textDob     = (EditText) rootView.findViewById(R.id.dob_profile_editText);
        textBloodType = (EditText) rootView.findViewById(R.id.bloodType_profile_editText);
        textAllergy = (EditText) rootView.findViewById(R.id.allergy_profile_editText);
        textMedicalRecord = (EditText) rootView.findViewById(R.id.medicalCondition_profile_editText);
        buttonSave        =  (Button) rootView.findViewById(R.id.button_save_profile);
        profileImage =  (CircleImageView) rootView.findViewById(R.id.image_profile);

        String currentUserName =   sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_USER);
        loadEditText();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textFullName.setText(textFullName.getText().toString());
                textAge.setText(textAge.getText());
                textDob.setText(textDob.getText());
                textBloodType.setText(textBloodType.getText());
                textAllergy.setText(textAllergy.getText());
                textMedicalRecord.setText(textMedicalRecord.getText());
                sharedPreferencesService.saveToSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_FULL_NANE,textFullName.getText().toString());
                sharedPreferencesService.saveToSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_AGE,textAge.getText().toString());
                sharedPreferencesService.saveToSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_DOB,textDob.getText().toString());
                sharedPreferencesService.saveToSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_BLOOD,textBloodType.getText().toString());
                sharedPreferencesService.saveToSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_ALLERGY,textAllergy.getText().toString());
                sharedPreferencesService.saveToSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_MEDICAL_RECORD,textMedicalRecord.getText().toString());
                sharedPreferencesService.saveToSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_PROFILE_PICTURE, transformToEncoding64());
                Toast.makeText(getActivity(),"Contact is updated",Toast.LENGTH_LONG).show();
            }
        });

        //so whenever user click the photo, then camera view will shown
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


        return rootView;
    }

    //when
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        profileImage.setImageBitmap(bitmap);
    }


    public void loadEditText(){
        if(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_FULL_NANE) != null){
            textFullName.setText(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_FULL_NANE));
        }
        if(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_DOB) != null){
            textDob.setText(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_DOB));
        }
        if(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_AGE) != null){
            textAge.setText(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_AGE));
        }
        if(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_BLOOD) != null){
            textBloodType.setText(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_BLOOD));
        }
        if(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_ALLERGY) != null){
            textAllergy.setText(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_ALLERGY));
        }
        if(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_MEDICAL_RECORD) != null){
            textMedicalRecord.setText(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_MEDICAL_RECORD) );
        }
        if(sharedPreferencesService.getDataFromSharedPreferences(SharedPreferencesKey.NAME_SHARED_PREFERENCES,SharedPreferencesKey.KEY_PROFILE_PICTURE) != null){
            byte[] decodedString = android.util.Base64.decode(sharedPreferencesService.getDataFromSharedPreferences(
                    SharedPreferencesKey.NAME_SHARED_PREFERENCES, SharedPreferencesKey.KEY_PROFILE_PICTURE
            ), android.util.Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profileImage.setImageBitmap(bitmap);
        }

    }

    public String transformToEncoding64(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeBytes(bb);
        return image;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
