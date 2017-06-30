package test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import team.even.jobcrawler.model.db.dbc.DatabaseConnection;
import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.factory.JobTypesDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.db.vo.JobTypes;
import team.even.jobcrawler.model.fileclear.FileClear;
import team.even.jobcrawler.model.filepath.FilePath;
import team.even.jobcrawler.model.proxy.ProxyGetter;

public class TEST
{
	public static void main(String[] args) throws Exception
	{
//		FileClear.clearFile(FilePath.DOWNLOADPATH);
//		System.out.println("Çå³ýÎÄ¼þ£¡");
		ProxyGetter getter = new ProxyGetter();
		List<Map<String, String>> list = getter.getProxy();
		for(Map map : list)
		{
			System.out.println(map.get("ip") + ":" + map.get("port"));
		}
		
		
//		JobDataDAOFactory dataFac = new JobDataDAOFactory();
//		List<JobTypes> list = new ArrayList<JobTypes>();
//		list = JobTypesDAOFactory.getJobTypesDAOInstance().findAll();
//		DatabaseConnection dbc;
//		try
//		{
//			for(JobTypes jt : list)
//			{
//				String kind = jt.getKind();
//				String workPlace = jt.getWorkPlace();
//				List<JobData> dataList = dataFac.getJobDataDAOInstance().findByKindandWorkPlace(kind, workPlace);
//				int count = dataList.size();
//				dbc = new DatabaseConnection();
//				Connection conn = dbc.getConnection();
//				String sql = "update jobTypes set amount = ? where kind = ? and workPlace = ?";
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//				pstmt.setInt(1, count);
//				pstmt.setString(2, kind);
//				pstmt.setString(3, workPlace);
//				System.out.println(pstmt.executeUpdate());
//				pstmt.close();
//				conn.close();
//			}
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
