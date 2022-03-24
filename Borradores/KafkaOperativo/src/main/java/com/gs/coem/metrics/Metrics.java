/*package com.gs.coem.metrics;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;

public class Metrics {

	
	public void GetMetrics() 
	{
		final String USAGE =
	            "To run this example, supply a metric name and metric namespace\n" +
	            "Ex: ListMetrics <metric-name> <metric-namespace>\n";

	        String name = args[0];
	        String namespace = args[1];

	        final AmazonCloudWatch cw =
	            AmazonCloudWatchClientBuilder.defaultClient();

	        ListMetricsRequest request = new ListMetricsRequest()
	                .withMetricName(name)
	                .withNamespace(namespace);

	        boolean done = false;

	        while(!done) {
	            ListMetricsResult response = cw.listMetrics(request);

	            for(Metric metric : response.getMetrics()) {
	                System.out.printf(
	                    "Retrieved metric %s", metric.getMetricName());
	            }

	            request.setNextToken(response.getNextToken());

	            if(response.getNextToken() == null) {
	                done = true;
	            }
	        }
	    }
	}
}

 */
