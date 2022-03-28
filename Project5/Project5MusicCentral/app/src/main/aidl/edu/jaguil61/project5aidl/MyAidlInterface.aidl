//Jose M. Aguilar (jaguil61)
// MyAidlInterface.aidl
package edu.jaguil61.project5aidl;

interface MyAidlInterface {
    List<String> getSongNames();
    List<String> getArtistNames();
    List<Bitmap> getAllSongCovers();
    List<String> getURLs();
}