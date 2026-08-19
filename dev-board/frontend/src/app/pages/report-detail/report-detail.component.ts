import { Component, inject } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { catchError, of, switchMap, tap } from 'rxjs';
import { BugReport } from '../../models/bug-report.model';
import { ReportService } from '../../services/report.service';

@Component({
  selector: 'app-report-detail',
  imports: [RouterLink],
  templateUrl: './report-detail.component.html',
  styleUrl: './report-detail.component.css'
})
export class ReportDetailComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly reportService = inject(ReportService);

  report: BugReport | null = null;
  isLoading = true;
  hasError = false;

  constructor() {
    this.route.paramMap.pipe(
      tap(() => {
        this.report = null;
        this.isLoading = true;
        this.hasError = false;
      }),
      switchMap((params) => this.reportService.getReport(params.get('uuid') ?? '').pipe(
        catchError(() => {
          this.hasError = true;
          return of(null);
        })
      ))
    ).subscribe((report) => {
      this.report = report;
      this.isLoading = false;
    });
  }
}
