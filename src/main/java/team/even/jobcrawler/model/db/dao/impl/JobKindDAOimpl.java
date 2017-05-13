package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import team.even.jobcrawler.model.db.dao.IJobKindDAO;
import team.even.jobcrawler.model.db.vo.JobKind;

public class JobKindDAOimpl implements IJobKindDAO
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public JobKindDAOimpl(Connection conn)
	{
		this.conn = conn;
	}
	
	@Override
	public boolean doCreate(JobKind jobKind)
	{
		boolean flag = false;
		String sql = "insert into jobkind values (?)";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jobKind.getKind());
			if(pstmt.executeUpdate() > 0)
			{
				flag = true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(conn != null)
					{
						try
						{
							conn.close();
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return flag;
	}

	@Override
	public List<JobKind> findAll()
	{
		List<JobKind> dataList = new ArrayList<JobKind>();
		String sql = "select * from jobkind";
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				JobKind data = new JobKind();
				data.setKind(rs.getString("kind"));
				dataList.add(data);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(pstmt != null)
					{
						try
						{
							pstmt.close();
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
						finally
						{
							if(conn != null)
							{
								try
								{
									conn.close();
								} catch (SQLException e)
								{
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		
		return dataList;
	}

}
