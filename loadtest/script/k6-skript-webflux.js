import {check} from 'k6';
import http from 'k6/http';

export function testWebflux() {
  let res = http.get('http://localhost:8080/webflux/users');
  check(res, {
    'is status 200': (r) => r.status === 200,
  });
}

export default function () {
  testWebflux();
}
