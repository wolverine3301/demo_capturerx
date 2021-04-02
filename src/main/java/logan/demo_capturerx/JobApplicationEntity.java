package logan.demo_capturerx;

import javax.persistence.*;

@Entity
@Table(name = "jobApplications")
public class JobApplicationEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	    private Integer jobApplicationId;
	    
	    @Column(name = "status", nullable = false)
	    private String status;


	 
	    public JobApplicationEntity() {
	         this.status = "new";

	    }
	   
	    public Integer getJobApplicationId() {
	        return jobApplicationId;
	    }
	    public void setId(Integer jobApplicationId) {
	        this.jobApplicationId = jobApplicationId;
	    }
	 
	   	public String getStatus() {
	        return status;
	    }
	    public void setStatus(String status) {
	        this.status = status;
	    }


	    @Override
	    public String toString() {
	        return "Employee [jobApplicationId=" + jobApplicationId + ", status=" + status + "]";
	    }
	 
	}
