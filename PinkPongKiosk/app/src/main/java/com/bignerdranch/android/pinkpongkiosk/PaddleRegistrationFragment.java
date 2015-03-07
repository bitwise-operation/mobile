package com.bignerdranch.android.pinkpongkiosk;


import android.app.Activity;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.pinkpongkiosk.model.Player;

import static com.bignerdranch.android.pinkpongkiosk.PaddleRegistrationActivity.PlayerTapListener;
import static com.bignerdranch.android.pinkpongkiosk.PaddleRegistrationActivity.newIntent;

//maybe animate use this: https://github.com/swarmnyc/AndroidFragmentAnimations/blob/master/app/src/main/res/anim/slide_out_left.xml?
//maybe use this for paddle animation: http://stackoverflow.com/questions/19519921/how-to-animate-imageview-from-center-crop-to-fill-the-screen-and-vice-versa-fac
public class PaddleRegistrationFragment extends Fragment implements PlayerTapListener {

    public static final String ARG_PLAYER = "player";
    public static final String ACTION_PADDLE_REG_FRAG_UPDATE_UI_COMPLETE = "com.bnr.android.paddleregistrationfragment.updateUiComplete";

    public void setRegistrationStateListener(PaddleRegFragmentListener listener) {
        mPaddleRegFragmentListener = listener;
    }


    private static String PROMPT_TAP = "Tap your paddle to the back of the tablet";
    private static String PROMPT_SUCCESS = "Success!";

    private static enum RegistrationState {
        PLAYER_PROMPT, PLAYER_SUCCESS
    }


    private Player mPlayer;
    private PaddleRegFragmentListener mPaddleRegFragmentListener;
    private RegistrationState mRegistrationState;

    private TextView mNameTextView;
    private TextView mPromptTextView;
    private ImageView mImageView;
    private TransitionDrawable mTransitionDrawable;


    public static PaddleRegistrationFragment newInstance(Player player){
        PaddleRegistrationFragment fragment = new PaddleRegistrationFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((PaddleRegistrationActivity)activity).setPlayerTapListener(this);
        setRegistrationStateListener((PaddleRegFragmentListener)activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayer = (Player) getArguments().getSerializable(ARG_PLAYER);
        mRegistrationState = RegistrationState.PLAYER_PROMPT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_paddle_registration, container, false);

        mNameTextView = (TextView) v.findViewById(R.id.fragment_paddle_reg_name_text_view);
        mPromptTextView = (TextView) v.findViewById(R.id.fragment_paddle_reg_prompt_text_view);
        mImageView = (ImageView) v.findViewById(R.id.fragment_paddle_reg_image_view);
        mTransitionDrawable = (TransitionDrawable) mImageView.getBackground();

        updateUI();

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((PaddleRegistrationActivity)getActivity()).setPlayerTapListener(null);
        setRegistrationStateListener(null);
    }

    @Override
    public void onPlayerTapped(String playerId) {
        switch (mRegistrationState) {
            case PLAYER_PROMPT:
                mRegistrationState = RegistrationState.PLAYER_SUCCESS;
                updateUI(); //reflect success in UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    mPaddleRegFragmentListener.onUiUpdateComplete();
                    }
                }, 5500);
                break;
        }
    }

    private void updateUI() {
        switch (mRegistrationState) {
            case PLAYER_PROMPT:
                updateUiPromptPlayer(mPlayer);
                break;
            case PLAYER_SUCCESS:
                updateUiSuccess();
                break;
        }
    }

    private void updateUiPromptPlayer(Player player) {
        mNameTextView.setText(player.getName());
        mPromptTextView.setText(PROMPT_TAP);
    }

    private void updateUiSuccess() {
        mPromptTextView.setText(PROMPT_SUCCESS);
        mTransitionDrawable.startTransition(5000);
    }

}
