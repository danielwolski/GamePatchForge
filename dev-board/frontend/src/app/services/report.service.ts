import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_CONFIG } from '../config/api.config';
import { BugReport } from '../models/bug-report.model';

@Injectable({ providedIn: 'root' })
export class ReportService {
  private readonly http = inject(HttpClient);
  private readonly reportsUrl = `${API_CONFIG.baseUrl}/reports`;

  getReports(): Observable<BugReport[]> {
    return this.http.get<BugReport[]>(this.reportsUrl);
  }

  getReport(uuid: string): Observable<BugReport> {
    return this.http.get<BugReport>(`${this.reportsUrl}/${encodeURIComponent(uuid)}`);
  }
}
