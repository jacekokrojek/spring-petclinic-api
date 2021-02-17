package contracts

import org.springframework.cloud.contract.spec.Contract
import org.springframework.cloud.contract.spec.internal.HttpMethods

Contract.make {
    request {
        method(HttpMethods.HttpMethod.GET)
        urlPath('api/vets/123')
    }

    response {
        status(200)
        headers {
            header(contentType(), applicationJsonUtf8())
        }
        body([
                id          : 123,
                firstName   : 'John',
                lastName    : 'Down',
                specialties: [id: 1, name: 'dentistry']
        ])
    }
}
