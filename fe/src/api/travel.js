import {
  travelKeywords,
  commonApi,
  travelsWithAuth,
  travels,
} from '@/api/index';

function getTravelKeywords() {
  return travelKeywords.get();
}

function getTravelInfoById(travelId) {
  return travels.get(`${travelId}`);
}

function getTravelInfoAroundCoordinate(longitude, latitude) {
  return commonApi.get(`travels?longitude=${longitude}&latitude=${latitude}`);
}

function getTravelInfoAroundCoordinateByKeywordId(
  keywordId,
  longitude,
  latitude,
) {
  return commonApi.get(
    `travels/search?keywordId=${keywordId}&longitude=${longitude}&latitude=${latitude}`,
  );
}

function registerTravel(data) {
  const formData = new FormData();

  formData.append('name', data.name);
  formData.append('description', data.description);
  formData.append('travelKeywordId', data.travelKeywordId);
  Array.from(data.files).forEach(file => {
    formData.append('files', file);
  });

  formData.append('longitude', data.longitude);
  formData.append('latitude', data.latitude);

  return travelsWithAuth.post('', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
}

export {
  getTravelKeywords,
  getTravelInfoById,
  getTravelInfoAroundCoordinate,
  getTravelInfoAroundCoordinateByKeywordId,
  registerTravel,
};
