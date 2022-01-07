package dlmartin.transaction.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.openapitools.api.TransactionApi;
import org.openapitools.model.InlineObject;
import org.openapitools.model.InlineResponse200;
import org.openapitools.model.InlineResponse2001;
import org.openapitools.model.InlineResponse2002;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import dlmartin.transaction.services.TransactionSrv;


@Controller
@RequestMapping("${openapi.transMng.base-path:}")
public class TransactionApiController implements TransactionApi {
	
	@Autowired
	private TransactionSrv transactionSrv;

    private final NativeWebRequest request;
    
    @Override
	public ResponseEntity<InlineResponse200> transactionGet(@Valid String accountIban, @Valid String sort) {
		// TODO Auto-generated method stub
		return TransactionApi.super.transactionGet(accountIban, sort);
	}

	@Override
	public ResponseEntity<InlineResponse2001> transactionPost(@Valid InlineObject inlineObject) {
		// TODO Auto-generated method stub
		return TransactionApi.super.transactionPost(inlineObject);
	}

	@Override
	public ResponseEntity<InlineResponse2002> transactionReferenceStatusGet(String reference, @Valid String channel) {
		// TODO Auto-generated method stub
		return TransactionApi.super.transactionReferenceStatusGet(reference, channel);
	}

	@Autowired
    public TransactionApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
