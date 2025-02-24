package com.example.mealplannerapp.data.FirestoreDataSource;

import com.example.mealplannerapp.meal.models.Meal;
import com.example.mealplannerapp.schedule.model.ScheduledMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class FirestoreDataSource {
    private static final String COLLECTION_FAVORITES = "favorites";
    private static final String COLLECTION_PLANNED_MEALS = "planned_meals";
    private static final String TAG = "FirestoreDataSource";

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;
    private final CollectionReference favoritesCollection;
    private final CollectionReference plannedMealsCollection;

    public FirestoreDataSource() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        favoritesCollection = firestore.collection(COLLECTION_FAVORITES);
        plannedMealsCollection = firestore.collection(COLLECTION_PLANNED_MEALS);
    }

    private String getUserId() {
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
    }

    public Completable addFavoriteMeal(Meal meal) {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Exception("User not authenticated"));
        }

        return Completable.create(emitter -> {
            favoritesCollection.document(userId)
                    .collection("meals")
                    .document(meal.getIdMeal())
                    .set(meal)
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable removeFavoriteMeal(Meal meal) {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Exception("User not authenticated"));
        }

        return Completable.create(emitter -> {
            favoritesCollection.document(userId)
                    .collection("meals")
                    .document(meal.getIdMeal())
                    .delete()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Observable<List<Meal>> getFavoriteMeals() {
        String userId = getUserId();
        if (userId == null) {
            return Observable.error(new Exception("User not authenticated"));
        }

        return Observable.create(emitter -> {
            favoritesCollection.document(userId)
                    .collection("meals")
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<Meal> meals = new ArrayList<>();

                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            meals.add(document.toObject(Meal.class));
                        }

                        emitter.onNext(meals);
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable addMealToFavorites(Meal meal) {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Exception("User not authenticated"));
        }

        return Completable.create(emitter -> {
            favoritesCollection.document(userId)
                    .collection("meals")
                    .document(meal.getIdMeal())
                    .set(meal)
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable addScheduledMeal(ScheduledMeal scheduledMeal) {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Exception("User not authenticated"));
        }

        return Completable.create(emitter -> {
            plannedMealsCollection.document(userId)
                    .collection(scheduledMeal.getDate())
                    .document(scheduledMeal.getMeal().getIdMeal())
                    .set(scheduledMeal)
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable removeScheduledMeal(ScheduledMeal scheduledMeal) {
        String userId = getUserId();
        if (userId == null) {
            return Completable.error(new Exception("User not authenticated"));
        }

        return Completable.create(emitter -> {
            plannedMealsCollection.document(userId)
                    .collection(scheduledMeal.getDate())
                    .document(scheduledMeal.getMeal().getIdMeal())
                    .delete()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Observable<List<ScheduledMeal>> getScheduledMealsByDate(String date) {
        String userId = getUserId();
        if (userId == null) {
            return Observable.error(new Exception("User not authenticated"));
        }

        return Observable.create(emitter -> {
            plannedMealsCollection.document(userId)
                    .collection(date)
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<ScheduledMeal> scheduledMeals = new ArrayList<>();

                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            scheduledMeals.add(document.toObject(ScheduledMeal.class));
                        }

                        emitter.onNext(scheduledMeals);
                        emitter.onComplete();
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
