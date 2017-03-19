package cyan.intellicyan.db.tables;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import cyan.intellicyan.db.InterfaceCRUD;

/**
 * Created by wx on 2017/3/9.
 */

public class TableTask implements InterfaceCRUD<TableTask.TaskInfo> {
    public static String makeTable() {
        StringBuilder sb =new StringBuilder();
        sb.append("create table ").append(Table.TNAME);
        sb.append(" ( ").append(Table.col.pid)
                .append(" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        sb.append(Table.col.datems).append(" LONG NOT NULL, ");
        sb.append(Table.col.timems).append(" LONG NOT NULL, ");
        sb.append(Table.col.datestr).append(" TEXT NOT NULL, ");
        sb.append(Table.col.timestr).append(" TEXT NOT NULL, ");
        sb.append(Table.col.start_dt).append(" LONG NOT NULL, ");
        sb.append(Table.col.end_dt).append(" LONG NOT NULL, ");
        sb.append(Table.col.title).append(" TEXT NOT NULL, ");
        sb.append(Table.col.content).append(" TEXT NOT NULL ");
        sb.append(");");
        return sb.toString();
    }

    @Override
    public void insert(Context ctx, ContentValues contentValues) {

    }

    @Override
    public void update(Context ctx, ContentValues contentValues, String where, String[] whereCause) {

    }

    @Override
    public List<TaskInfo> find(Context ctx, String where, String[] whereCause) {
        return null;
    }

    @Override
    public void del(Context ctx, String where, String[] whereCause) {

    }

    @Override
    public List<TaskInfo> find(Context ctx, String raw) {
        return null;
    }


    public static class Table{
        public static String TNAME="table_task";
        public static enum col{
            pid,datems,timems,datestr,timestr,start_dt,end_dt,title,content
        }
    }

    public static class TaskInfo implements Parcelable{
        public int pid;
        public long datems;
        public long timems;
        public String datestr;
        public String timestr;
        public long startdt;
        public long enddt;
        public String title;
        public String content;

        protected TaskInfo(Parcel in) {
            pid = in.readInt();
            datems = in.readLong();
            timems = in.readLong();
            datestr = in.readString();
            timestr = in.readString();
            startdt = in.readLong();
            enddt = in.readLong();
            title = in.readString();
            content = in.readString();
        }

        public static final Creator<TaskInfo> CREATOR = new Creator<TaskInfo>() {
            @Override
            public TaskInfo createFromParcel(Parcel in) {
                return new TaskInfo(in);
            }

            @Override
            public TaskInfo[] newArray(int size) {
                return new TaskInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(pid);
            dest.writeLong(datems);
            dest.writeLong(timems);
            dest.writeString(datestr);
            dest.writeString(timestr);
            dest.writeLong(startdt);
            dest.writeLong(enddt);
            dest.writeString(title);
            dest.writeString(content);
        }
    }
}
