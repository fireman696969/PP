package com.example.pollutionpals.UI.MyReportsPage;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.pollutionpals.Data.DB.MyDatabaseHelper;
import com.example.pollutionpals.Data.DB.ReportsDatabase;

public class ReportsModel {
    private ReportsDatabase reportsDatabase;
    private MyDatabaseHelper citizensDatabase;
    private String userId;

    public ReportsModel(Context context, String userId) {
        this.reportsDatabase = new ReportsDatabase(context);
        this.citizensDatabase = new MyDatabaseHelper(context);
        this.userId = userId;
    }

    public interface FetchReportsCallback {
        void onReportsFetched(ReportCursorWrapper cursor);
        void onFetchFailed(Exception e);
    }

    public void fetchReports(FetchReportsCallback callback) {
        new AsyncTask<Void, Void, ReportCursorWrapper>() {
            private Exception exception;

            @Override
            protected ReportCursorWrapper doInBackground(Void... voids) {
                try {
                    Cursor cursor;
                    if (userId.equals("329455109")) {
                        cursor = reportsDatabase.GetAllReports();
                    } else {
                        cursor = reportsDatabase.GetReportsById(userId);
                    }
                    return new ReportCursorWrapper(cursor);
                } catch (Exception e) {
                    exception = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ReportCursorWrapper result) {
                if (exception != null) {
                    callback.onFetchFailed(exception);
                } else {
                    callback.onReportsFetched(result);
                }
            }
        }.execute();
    }

    public interface FetchReportDetailsCallback {
        void onReportDetailsFetched(ReportDetails reportDetails);
        void onFetchFailed(Exception e);
    }

    public void fetchReportDetails(int position, FetchReportDetailsCallback callback) {
        new AsyncTask<Void, Void, ReportDetails>() {
            private Exception exception;

            @Override
            protected ReportDetails doInBackground(Void... voids) {
                try {
                    Cursor cursor = reportsDatabase.GetReportsById(userId);
                    cursor.moveToPosition(position);

                    String idPerson = cursor.getString(1);
                    String date = cursor.getString(5);
                    String description = cursor.getString(3);
                    String status = cursor.getString(7);
                    int points = cursor.getInt(6);
                    String location = cursor.getString(4);
                    byte[] byteArray = cursor.getBlob(2);
                    Bitmap img = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                    return new ReportDetails(idPerson, date, description, status, points, location, img);
                } catch (Exception e) {
                    exception = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ReportDetails result) {
                if (exception != null) {
                    callback.onFetchFailed(exception);
                } else {
                    callback.onReportDetailsFetched(result);
                }
            }
        }.execute();
    }

    public interface ReportUpdateCallback {
        void onUpdateSuccess();
        void onUpdateFailed(Exception e);
    }

    public void approveReport(int reportId, ReportUpdateCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            private Exception exception;

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    // Assume necessary methods are in the citizensDatabase and reportsDatabase to update the status and points
                    reportsDatabase.updateReportStatus(String.valueOf(reportId), "approved");
                    // Update points for the citizen
                    return true;
                } catch (Exception e) {
                    exception = e;
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (exception != null || !success) {
                    callback.onUpdateFailed(exception != null ? exception : new Exception("Failed to approve report"));
                } else {
                    callback.onUpdateSuccess();
                }
            }
        }.execute();
    }

    public void denyReport(int reportId, ReportUpdateCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            private Exception exception;

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    reportsDatabase.updateReportStatus(String.valueOf(reportId), "denied");
                    return true;
                } catch (Exception e) {
                    exception = e;
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (exception != null || !success) {
                    callback.onUpdateFailed(exception != null ? exception : new Exception("Failed to deny report"));
                } else {
                    callback.onUpdateSuccess();
                }
            }
        }.execute();
    }

    public static class ReportCursorWrapper {
        private Cursor cursor;

        public ReportCursorWrapper(Cursor cursor) {
            this.cursor = cursor;
        }

        public Cursor getCursor() {
            return cursor;
        }

        public int getCount() {
            return cursor.getCount();
        }

        public void moveToFirst() {
            cursor.moveToFirst();
        }

        public void moveToNext() {
            cursor.moveToNext();
        }

        public String getString(int columnIndex) {
            return cursor.getString(columnIndex);
        }

        public int getInt(int columnIndex) {
            return cursor.getInt(columnIndex);
        }

        public byte[] getBlob(int columnIndex) {
            return cursor.getBlob(columnIndex);
        }

        public void moveToPosition(int position) {
            cursor.moveToPosition(position);
        }
    }

    public static class ReportDetails {
        private String idPerson;
        private String date;
        private String description;
        private String status;
        private int points;
        private String location;
        private Bitmap image;

        public ReportDetails(String idPerson, String date, String description, String status, int points, String location, Bitmap image) {
            this.idPerson = idPerson;
            this.date = date;
            this.description = description;
            this.status = status;
            this.points = points;
            this.location = location;
            this.image = image;
        }

        public String getIdPerson() {
            return idPerson;
        }

        public String getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }

        public String getStatus() {
            return status;
        }

        public int getPoints() {
            return points;
        }

        public String getLocation() {
            return location;
        }

        public Bitmap getImage() {
            return image;
        }
    }
}
