package com.mert.detail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Developer;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name="summarize", defaultPhase= LifecyclePhase.COMPILE)
public class ProjectDetail extends AbstractMojo{
	
	@Parameter(defaultValue = "${project}", required = true)	
	private MavenProject project;

	@Parameter(defaultValue = "${project.build.directory}")
	private File outputDirectory;

		public void execute() throws MojoExecutionException, MojoFailureException {
			// TODO Auto-generated method stub
			
			// Assigning project features like version, groupId, artifactId
			String version = project.getVersion();
			String groupId = project.getGroupId();
			String artifactId = project.getArtifactId();
			
			// Assigning project dependecy, plugin, developer and propert meta data
			List<Dependency> dependencies = project.getDependencies();
			List<Plugin> plugins = project.getBuildPlugins();
			List<Developer> developers = project.getDevelopers();
			String property = project.getProperties().getProperty("release.date");
			
			// Checking specified directory exist or not
			File f = outputDirectory;
			if(!f.exists()) {
				f.mkdirs();
			}
			
			// Creating text file
			File output = new File(f , "outputFile.txt");
			
			
			// Writing all details of project to text file
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(output));
				writer.write("Project info : " + groupId + "." + artifactId + "." + version + " Developers : ");
				for(int i = 0; i < developers.size(); i++) {
					writer.write("Developer " +  (i+1) + " Name : " + developers.get(i).getName() + " ");
				}
				writer.write("Release Date : " + property + " Dependencies ");
				for(int i = 0; i < dependencies.size(); i++) {
					writer.write("Dependency " + (i+1) + ": " + dependencies.get(i).getGroupId() + "." +dependencies.get(i).getArtifactId() + " ");
				}
				writer.write("Plugins ");
				for(int i = 0; i < plugins.size(); i++) {
					writer.write(" Plugin " + (i+1) + ": " + plugins.get(i).getArtifactId());
				}
				writer.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}

}
