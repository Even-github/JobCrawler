package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import team.even.jobcrawler.model.db.dbc.DatabaseConnection;
import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.factory.JobTypesDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.db.vo.JobTypes;

public class TEST
{
	public static void main(String[] args)
	{
		JobTypesDAOFactory factory = new JobTypesDAOFactory();
		JobDataDAOFactory dataFac = new JobDataDAOFactory();
		List<JobTypes> list = new ArrayList<JobTypes>();
		list = factory.getJobTypesDAOInstance().findAll();
		DatabaseConnection dbc;
		try
		{
			for(JobTypes jt : list)
			{
				String kind = jt.getKind();
				String workPlace = jt.getWorkPlace();
				List<JobData> dataList = dataFac.getJobDataDAOInstance().findByKindandWorkPlace(kind, workPlace);
				int count = dataList.size();
				dbc = new DatabaseConnection();
				Connection conn = dbc.getConnection();
				String sql = "update jobTypes set amount = ? where kind = ? and workPlace = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, count);
				pstmt.setString(2, kind);
				pstmt.setString(3, workPlace);
				System.out.println(pstmt.executeUpdate());
				pstmt.close();
				conn.close();
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
