import { travelKeywords, travels } from '@/api/index';

function getTravelKeywords() {
  return travelKeywords.get();
}

function registerTravel(data) {
  return travels.post('', data);
}

export { getTravelKeywords, registerTravel };
