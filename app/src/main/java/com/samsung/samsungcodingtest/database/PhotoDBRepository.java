package com.samsung.samsungcodingtest.database;

import android.util.Log;

import com.samsung.samsungcodingtest.model.PhotoDetails;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;

public class PhotoDBRepository {
    private static final String TAG = PhotoDBRepository.class.getCanonicalName();
    private static PhotoDBRepository instance = null;

    /*private PhotoDBRepository(){

    }*/

    public boolean savePhotos(ArrayList<PhotoDetails> photoDetailsArrayList){
        Realm realm = Realm.getDefaultInstance();
        try {
            final RealmList<PhotoDetails> photoRealmList = new RealmList();
            photoRealmList.addAll(photoDetailsArrayList);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(photoRealmList);
                }
            });

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return false;
        } finally {
            realm.close();
        }
        return true;
    }

    public boolean updatePhoto(boolean isFavorite,int id){
        Realm realm = Realm.getDefaultInstance();
        try {
            final PhotoDetails photoDetails = realm.where((PhotoDetails.class)).equalTo("id", id).findFirst();
            if(photoDetails != null) {
                photoDetails.setFavorite(isFavorite);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(photoDetails);
                    }
                });
            }
        }catch (Exception ex){
            Log.e(TAG, ex.getMessage());
            return false;
        } finally {
            realm.close();
        }
        return true;
    }

    public boolean getPhotoFavoriteStatus(int id){
        Realm realm = Realm.getDefaultInstance();
        try {
            final PhotoDetails photoDetails = realm.where((PhotoDetails.class)).equalTo("id", id).findFirst();
            if(photoDetails != null) {
                return photoDetails.isFavorite();
            }
        }catch (Exception ex){
            Log.e(TAG, ex.getMessage());
        }finally {
            realm.close();
        }
        return false;
    }
}
