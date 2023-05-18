function getAccessTokenFromCookie() {
  return document.cookie.replace(
    /(?:(?:^|.*;\s*)auth\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
}

function deleteCookie(value) {
  document.cookie = `${value}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
}

export { getAccessTokenFromCookie, deleteCookie };
