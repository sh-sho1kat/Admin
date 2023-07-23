package com.example.admin.userpart.rating;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.MotionButton;

import com.example.admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RateUsDialogue extends Dialog {
    private ImageView rating_image;
    private MotionButton MayBeLaterButton,SubmitNowButton;
    private RatingBar MyRatingBar;
    private DatabaseReference reference;
    private float userRating=0;
    public RateUsDialogue(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_us_dialogue_layout);

        rating_image = findViewById(R.id.rating_image);
        MayBeLaterButton = findViewById(R.id.MayBeLaterButton);
        SubmitNowButton = findViewById(R.id.SubmitRatingButton);
        MyRatingBar = findViewById(R.id.myRatingBar);

        reference = FirebaseDatabase.getInstance().getReference().child("Ratings");

        MyRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating<=1)
                {
                    rating_image.setImageResource(R.drawable.rating_1);
                }
                else if(rating<=2)
                {
                    rating_image.setImageResource(R.drawable.rating_2);
                }
                else if(rating<=3)
                {
                    rating_image.setImageResource(R.drawable.rating_3);
                }
                else if(rating<=4)
                {
                    rating_image.setImageResource(R.drawable.rating_4);
                }
                else if(rating<=5)
                {
                    rating_image.setImageResource(R.drawable.rating_5);
                }
                animateImage(rating_image);
                userRating =rating;
            }
        });

        MayBeLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        SubmitNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uniquekey = reference.push().getKey();
                reference.child(uniquekey).setValue(userRating).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Thanks For Rating My App", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                dismiss();

            }
        });
    }
    private void animateImage(ImageView ratingImage)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);
    }
}
