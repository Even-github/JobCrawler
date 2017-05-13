package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import team.even.jobcrawler.model.db.dao.IJobDataDAO;
import team.even.jobcrawler.model.db.vo.JobData;

/**
 * 接口IJobDataDAO的真实实现类
 * @author 曾裕文
 *
 */
public class JobDataDAOimpl implements IJobDataDAO
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	
	public JobDataDAOimpl(Connection conn)
	{
		this.conn = conn;
	}
	
	@Override
	public boolean doCreate(JobData jobData)
	{
		boolean flag = false; //判断插入信息是否成功
		String sql = "insert into jobdata"
				+ "(kind,job,recruitingUnit,salary,workPlace,experience,academic,workType,url)"
				+ "values (?,?,?,?,?,?,?,?,?)";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jobData.getKind());
			pstmt.setString(2, jobData.getJob());
			pstmt.setString(3, jobData.getRecruitingUnit());
			pstmt.setString(4, jobData.getSalary());
			pstmt.setString(5, jobData.getWorkPlace());
			pstmt.setString(6, jobData.getExperience());
			pstmt.setString(7, jobData.getAcademic());
			pstmt.setString(8, jobData.getWorkType());
			pstmt.setString(9, jobData.getUrl());
			if(pstmt.executeUpdate() == 1) //插入成功
			{
				flag = true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(conn != null)
					{
						conn.close();						
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return flag;
	}

	@Override
	public boolean doDelete(String column, String value)
	{
		boolean flag = false; //判断删除操作是否成功
		String sql = "delete from jobdata where " + column + " = ?";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
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
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(conn != null)
					{
						conn.close();						
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	@Override
	public List<JobData> findAll()
	{
		ResultSet rs = null;
		List<JobData> dataList = new ArrayList<JobData>();
		String sql = "select * from jobdata";
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				JobData data = new JobData();
				data.setId(rs.getInt("id"));
				data.setKind(rs.getString("kind"));
				data.setJob(rs.getString("job"));
				data.setRecruitingUnit(rs.getString("recruitingUnit"));
				data.setSalary(rs.getString("salary"));
				data.setWorkPlace(rs.getString("workPlace"));
				data.setExperience(rs.getString("experience"));
				data.setAcademic(rs.getString("academic"));
				data.setWorkType(rs.getString("workType"));
				data.setUrl(rs.getString("url"));
				dataList.add(data);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt != null)
				{
					stmt.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(conn != null)
					{
						conn.close();						
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		return dataList;
	}

	@Override
	public List<JobData> find(String column, String value)
	{
		ResultSet rs = null;
		List<JobData> dataList = new ArrayList<JobData>();
		String sql = "select * from jobdata where " + column + " = ?";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				JobData data = new JobData();
				data.setId(rs.getInt("id"));
				data.setKind(rs.getString("kind"));
				data.setJob(rs.getString("job"));
				data.setRecruitingUnit(rs.getString("recruitingUnit"));
				data.setSalary(rs.getString("salary"));
				data.setWorkPlace(rs.getString("workPlace"));
				data.setExperience(rs.getString("experience"));
				data.setAcademic(rs.getString("academic"));
				data.setWorkType(rs.getString("workType"));
				data.setUrl(rs.getString("url"));
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
					try
					{
						if(pstmt != null)
						{
							pstmt.close();
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
					finally
					{
						try
						{
							if(conn != null)
							{
								conn.close();						
							}
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return dataList;
	}

	@Override
	public List<JobData> findByKindandWorkPlace(String kind, String workPlace)
	{
		ResultSet rs = null;
		List<JobData> dataList = new ArrayList<JobData>();
		String sql = "select * from jobdata where kind = ? and workPlace = ?";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, workPlace);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				JobData data = new JobData();
				data.setId(rs.getInt("id"));
				data.setKind(rs.getString("kind"));
				data.setJob(rs.getString("job"));
				data.setRecruitingUnit(rs.getString("recruitingUnit"));
				data.setSalary(rs.getString("salary"));
				data.setWorkPlace(rs.getString("workPlace"));
				data.setExperience(rs.getString("experience"));
				data.setAcademic(rs.getString("academic"));
				data.setWorkType(rs.getString("workType"));
				data.setUrl(rs.getString("url"));
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
					try
					{
						if(pstmt != null)
						{
							pstmt.close();
						}
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
					finally
					{
						try
						{
							if(conn != null)
							{
								conn.close();						
							}
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return dataList;
	}

	@Override
	public boolean doDeleteByKindandWorkPlace(String kind, String workPlace)
	{
		boolean flag = false; //判断删除操作是否成功
		String sql = "delete from jobdata where kind = ? and workPlace = ?";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, workPlace);
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
			try
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(conn != null)
					{
						conn.close();						
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
