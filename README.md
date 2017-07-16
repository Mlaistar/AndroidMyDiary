# AndroidMyDiary

This app uses:
- Thread to delay the progressBar for 5 seconds when loging in.
- SharedPreference to get and set values stored in XML file which is stored in /data/data/package/shared_prefs.

To read and write from external storage/file you need to add these two lines in your manifest:
  - <<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>>
  - <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
