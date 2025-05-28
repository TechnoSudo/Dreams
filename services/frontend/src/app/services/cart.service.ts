import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cart } from '../interfaces/cart';
import { AddCartItem } from '../interfaces/cart';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = `${environment.apiUrl}/api/carts`;

  constructor(private http: HttpClient) { }

  getCart(userUuid: string): Observable<Cart> {
    return this.http.get<Cart>(`${this.apiUrl}/${userUuid}`);
  }

  addItemToCart(userUuid: string, item: AddCartItem): Observable<Cart> {
    return this.http.post<Cart>(`${this.apiUrl}/${userUuid}/items`, item);
  }
}
