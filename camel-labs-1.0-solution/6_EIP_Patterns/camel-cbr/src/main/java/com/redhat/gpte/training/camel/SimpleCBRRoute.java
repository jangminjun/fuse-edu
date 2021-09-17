package com.redhat.gpte.training.camel;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCBRRoute extends RouteBuilder {

    @EndpointInject(uri="sourceDirectoryCsv")
    Endpoint sourceUri;

    @EndpointInject(uri="euroSinkCsv")
    Endpoint euroSinkUri;

    @EndpointInject(uri="usdSinkCsv")
    Endpoint usdSinkUri;

    @EndpointInject(uri="otherSinkCsv")
    Endpoint otherSinkUri;

	@Override
	public void configure() throws Exception {

		from(sourceUri)
            .log(">> Processing CSV files - ${file:onlyname} <<")
			.choice()
				.when()
                    .simple("${file:onlyname} == 'EUPayments.txt'")
                        .log("This is an Euro Payment: ${file:onlyname}")
	    				.to(euroSinkUri)
				.when()
                    .simple("${file:onlyname} == 'USPayments.txt'")
                        .log("This is an USD Payment: ${file:onlyname}")
	    				.to(usdSinkUri)
				.otherwise()
                    .log("This is an Other Currency Payment: ${file:onlyname}")
					.to(otherSinkUri);
	}

}
