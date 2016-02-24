package org.waikato.cloud.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Region;

public class S3BucketHandler 
{

	public static boolean createBucket( String bucketName )
	{
		boolean created = false;
		try
		{
			AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
			
			CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName, Region.AP_Sydney).withCannedAcl(CannedAccessControlList.BucketOwnerFullControl);	            
	        s3Client.createBucket(createBucketRequest);
	        
			created = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return created;
	}
	
	public static boolean createFolder( String folderName )
	{
		boolean created = false;
		try
		{
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(0);
			
			InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

			PutObjectRequest putObjectRequest = new PutObjectRequest(AmazonConstants.BUCKET_NAME, folderName + "/", emptyContent, metadata);
			
			AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
			s3Client.putObject(putObjectRequest);
			created = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return created;
	}
	
	public static boolean deleteFile( String bucketName, String fileName )
	{
		boolean deleted = false;
		try
		{			
			AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
			s3Client.deleteObject(bucketName, fileName);
			deleted = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return deleted;
	}
}
