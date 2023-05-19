import { travelKeywords } from '@/api/index';

function getTravelKeywords() {
  return travelKeywords.get();
}

export { getTravelKeywords };
