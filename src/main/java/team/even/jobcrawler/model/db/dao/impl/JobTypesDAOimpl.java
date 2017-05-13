package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import team.even.jobcrawler.model.db.dao.IJobTypesDAO;
import team.even.jobcrawler.model.db.vo.JobTypes;

public class JobTypesDAOimpl implements IJobTypesDAO
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Statement stmt = null;
	
	public JobTypesDAOimpl(Connection conn)
	{
		this.conn = conn;
	}
	
	@Override
	public boolean doCreate(JobTypes jobTypes)
	{
		boolean flag = false;
		String sql = "insert into jobtypes values (?,?,?)";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, jobTypes.getKind());
			pstmt.setString(2, jobTypes.getWorkPlace());
			pstmt.setInt(3, jobTypes.getAmount());
			if(pstmt.executeUpdate() == 1)
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
	public boolean doDelete(String kind, String workPlace)
	{
		boolean flag = false;
		String sql = "delete from jobtypes where kind = ? and workPlace = ?";
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

	@Override
	public List<JobTypes> findAll()
	{
		List<JobTypes> list = new ArrayList<JobTypes>();
		String sql = "select * from jobtypes";
		try
		{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				JobTypes jobTypes = new JobTypes();
				jobTypes.setKind(rs.getString("kind"));
				jobTypes.setWorkPlace(rs.getString("workPlace"));
				jobTypes.setAmount(rs.getInt("amount"));
				list.add(jobTypes);
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
						stmt.close();
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
		
		return list;
	}

	@Override
	public List<JobTypes> findByKindandWorkPlace(String kind, String workPlace)
	{
		List<JobTypes> list = new ArrayList<JobTypes>();
		String sql = "select * from jobtypes where kind = ? and workPlace = ?";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			pstmt.setString(2, workPlace);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				JobTypes jobTypes = new JobTypes();
				jobTypes.setKind(rs.getString("kind"));
				jobTypes.setWorkPlace(rs.getString("workPlace"));
				jobTypes.setAmount(rs.getInt("amount"));
				list.add(jobTypes);
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
		return list;
	}
}
