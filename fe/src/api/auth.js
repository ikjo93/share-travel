import { naverOauth2, kakaoOauth2, googleOauth2 } from './index';

function oauth2Login(provider, code) {
  if (provider === 'naver') {
    return naverOauth2.get(code);
  } else if (provider === 'kakao') {
    return kakaoOauth2.get(code);
  } else if (provider === 'google') {
    return googleOauth2.get(code);
  }

  alert('지원되지 않는 provider 입니다.');
}

export { oauth2Login };