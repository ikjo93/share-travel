import { deleteCookie } from '@/utils/cookies';

function logoutUtil() {
  this.$store.commit('LOGOUT');
  if (this.$route.path !== '/') {
    this.$router.push('/');
  }
  deleteCookie('renew');
}

export { logoutUtil };
