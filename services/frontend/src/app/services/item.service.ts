import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Item } from '../interfaces/item';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private apiUrl = `${environment.apiUrl}/api/items`;

  constructor(private http: HttpClient) { }

  getItems(records: number = 10, tags: string[] = []): Observable<Item[]> {
    const params: any = { records };
    if (tags.length > 0) {
      params.tags = tags.join(',');
    }
    return this.http.get<Item[]>(this.apiUrl, { params });
  }

  getItem(itemUuid: string): Observable<Item> {
    return this.http.get<Item>(`${this.apiUrl}/${itemUuid}`);
  }
}
