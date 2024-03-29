const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;

export const KAKAO_AUTH_TOKEN_URL = `https://kauth.kakao.com/oauth/token?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&`;
