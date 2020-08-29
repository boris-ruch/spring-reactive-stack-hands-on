import {check} from 'k6';
import http from 'k6/http';

export function testMVCCF() {
    let res = http.get('http://localhost:8080/mvccf/users');
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}

export default function () {
    testMVCCF();
}
