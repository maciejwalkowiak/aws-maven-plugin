package com.github.davidmoten.aws.maven;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsSyncClientBuilder;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * An abstract class that takes over the process of creation of an AWS client with the configured AWS credentials,
 * region and proxy. The child classes are supposed to use the client as is to access the AWS services.
 *
 * @param <Client> the type of the client used to access AWS services.
 * @param <Builder> the type of the builder used to build the client.
 */
public abstract class AbstractDeployAwsMojo<Builder extends AwsSyncClientBuilder<Builder, Client>, Client> extends AbstractAwsMojo {

    private final Builder builder;

    public AbstractDeployAwsMojo(Builder clientBuilder) {
        this.builder = clientBuilder;
    }

    @Override
    protected void execute(AWSCredentialsProvider awsCredentials, String region, Proxy proxy)
            throws MojoExecutionException, MojoFailureException {
        Client serviceClient = builder.withRegion(region)
                .withCredentials(awsCredentials)
                .withClientConfiguration(Util.createConfiguration(proxy))
                .build();
        execute(serviceClient);
    }

    protected abstract void execute(Client client) throws MojoExecutionException, MojoFailureException;

}
