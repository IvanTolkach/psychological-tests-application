import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 200 }, // Разгон
        { duration: '1m', target: 200 },  // Плато
        { duration: '10s', target: 0 },   // Завершение
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'],   // Тест упадет, если ошибок > 1%
        http_req_duration: ['p(95)<200'], // 95% запросов должны быть быстрее 200мс
    },
};

export default function () {
    const url = 'http://host.docker.internal:8080/api/test-attempts/search';

    const token = 'YOUR_TOKEN';

    const payload = JSON.stringify({});

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
    };

    const res = http.post(url, payload, params);

    const isOk = check(res, {
        'status is 200': (r) => r.status === 200,
        'body is array': (r) => {
            try {
                return Array.isArray(JSON.parse(r.body));
            } catch (e) {
                return false;
            }
        },
        'contains attempt data': (r) => {
            try {
                const body = JSON.parse(r.body);

                if (body.length > 0) {
                    return body[0].hasOwnProperty('studentId') && body[0].hasOwnProperty('testId');
                }
                return true;
            } catch (e) {
                return false;
            }
        },
    });

    if (!isOk && __VU === 1) {
        console.error(`[VU ${__VU}] Failed! Status: ${res.status}. Response: ${res.body.substring(0, 100)}...`);
    }

    sleep(0.1);
}