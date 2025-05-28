export interface Review {
  uuid: string;
  itemUuid: string;
  userUuid: string;
  rating: number;
  comment: string;
}

export interface CreateReview {
  rating: number;
  comment: string;
}
